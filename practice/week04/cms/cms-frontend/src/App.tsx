import './App.css'

import { useEffect, useMemo, useState } from 'react'

type Article = {
  id?: number
  title: string
  body: string
  status: string
  createdAt?: string
  updatedAt?: string
}

const API_BASE = 'http://localhost:8080/api/articles'

function App() {
  const [articles, setArticles] = useState<Article[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [editing, setEditing] = useState<Article | null>(null)
  const [filter, setFilter] = useState('')

  const emptyForm: Article = useMemo(
    () => ({ title: '', body: '', status: 'DRAFT' }),
    [],
  )
  const [form, setForm] = useState<Article>(emptyForm)

  const filteredArticles = useMemo(
    () =>
      articles.filter((a) =>
        [a.title, a.body]
          .join(' ')
          .toLowerCase()
          .includes(filter.toLowerCase()),
      ),
    [articles, filter],
  )

  useEffect(() => {
    void loadArticles()
  }, [])

  async function loadArticles() {
    try {
      setLoading(true)
      setError(null)
      const res = await fetch(API_BASE)
      if (!res.ok) {
        throw new Error(`Failed to load articles (${res.status})`)
      }
      const data = (await res.json()) as Article[]
      setArticles(data)
    } catch (e) {
      setError((e as Error).message)
    } finally {
      setLoading(false)
    }
  }

  function handleChange(
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>,
  ) {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError(null)
    try {
      const isEdit = !!editing?.id
      const url = isEdit ? `${API_BASE}/${editing!.id}` : API_BASE
      const method = isEdit ? 'PUT' : 'POST'
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      })
      if (!res.ok) {
        throw new Error('Không thể lưu bài viết')
      }
      await loadArticles()
      setEditing(null)
      setForm(emptyForm)
    } catch (e) {
      setError((e as Error).message)
    }
  }

  function startCreate() {
    setEditing(null)
    setForm(emptyForm)
  }

  function startEdit(article: Article) {
    setEditing(article)
    setForm({
      title: article.title,
      body: article.body,
      status: article.status,
    })
  }

  async function handleDelete(id: number) {
    if (!confirm('Bạn có chắc muốn xóa bài viết này?')) return
    try {
      setError(null)
      const res = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' })
      if (!res.ok) throw new Error('Không thể xóa bài viết')
      await loadArticles()
    } catch (e) {
      setError((e as Error).message)
    }
  }

  return (
    <div className="cms-layout">
      <header className="cms-header">
        <h1>CMS Dashboard</h1>
        <p>Quản lý bài viết (Layered + Microkernel)</p>
      </header>

      <main className="cms-main">
        <section className="cms-sidebar">
          <h2>Modules</h2>
          <ul>
            <li className="active">Articles</li>
            <li>Users (plugin)</li>
            <li>Media (plugin)</li>
          </ul>
          <button className="primary" onClick={startCreate}>
            + Bài viết mới
          </button>
        </section>

        <section className="cms-content">
          <div className="toolbar">
            <input
              type="text"
              placeholder="Tìm kiếm theo tiêu đề/nội dung..."
              value={filter}
              onChange={(e) => setFilter(e.target.value)}
            />
            <button onClick={loadArticles} disabled={loading}>
              Làm mới
            </button>
          </div>

          {error && <div className="alert error">{error}</div>}

          <div className="grid">
            <div className="panel">
              <h2>{editing ? 'Sửa bài viết' : 'Tạo bài viết mới'}</h2>
              <form className="article-form" onSubmit={handleSubmit}>
                <label>
                  Tiêu đề
                  <input
                    name="title"
                    value={form.title}
                    onChange={handleChange}
                    required
                  />
                </label>

                <label>
                  Nội dung
                  <textarea
                    name="body"
                    rows={6}
                    value={form.body}
                    onChange={handleChange}
                    required
                  />
                </label>

                <label>
                  Trạng thái
                  <select
                    name="status"
                    value={form.status}
                    onChange={handleChange}
                  >
                    <option value="DRAFT">Draft</option>
                    <option value="PUBLISHED">Published</option>
                  </select>
                </label>

                <div className="form-actions">
                  <button type="submit" className="primary" disabled={loading}>
                    {editing ? 'Cập nhật' : 'Tạo mới'}
                  </button>
                  {editing && (
                    <button
                      type="button"
                      onClick={startCreate}
                      className="ghost"
                    >
                      Hủy chỉnh sửa
                    </button>
                  )}
                </div>
              </form>
            </div>

            <div className="panel">
              <h2>Danh sách bài viết</h2>
              {loading && <p>Đang tải...</p>}
              {!loading && filteredArticles.length === 0 && (
                <p>Chưa có bài viết nào.</p>
              )}
              <ul className="article-list">
                {filteredArticles.map((a) => (
                  <li key={a.id} className="article-item">
                    <div>
                      <h3>{a.title}</h3>
                      <p className="meta">
                        {a.status} ·{' '}
                        {a.updatedAt &&
                          new Date(a.updatedAt).toLocaleString('vi-VN')}
                      </p>
                      <p className="excerpt">
                        {a.body.length > 120
                          ? `${a.body.slice(0, 120)}...`
                          : a.body}
                      </p>
                    </div>
                    <div className="item-actions">
                      <button onClick={() => startEdit(a)}>Sửa</button>
                      {a.id && (
                        <button
                          className="danger"
                          onClick={() => handleDelete(a.id!)}
                        >
                          Xóa
                        </button>
                      )}
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </section>
      </main>
    </div>
  )
}

export default App
