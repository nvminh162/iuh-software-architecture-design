import { useEffect, useMemo, useState } from 'react'
import './App.css'

function App() {
  const [dishes, setDishes] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    const loadFoods = async () => {
      try {
        const response = await fetch('/api/foods')
        if (!response.ok) {
          throw new Error('Cannot load food data from backend')
        }
        const data = await response.json()
        setDishes(data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadFoods()
  }, [])

  const categories = useMemo(() => {
    if (dishes.length === 0) {
      return []
    }

    const map = new Map()
    dishes.forEach((item) => {
      if (!map.has(item.category)) {
        map.set(item.category, {
          name: item.category,
          time: item.estimatedTime,
          rating: Number(item.rating).toFixed(1),
        })
      }
    })

    return Array.from(map.values()).slice(0, 4)
  }, [dishes])

  const testimonials = [
    {
      quote:
        'Mon an den nhanh, dong goi dep va con nong. Trai nghiem dat mon rat muot tren dien thoai.',
      name: 'Minh Anh',
      role: 'Product Designer',
    },
    {
      quote:
        'Ban do tai xe chinh xac, toi theo doi duoc don hang tung phut. Dich vu cham soc rat tot.',
      name: 'Quoc Dat',
      role: 'Startup Founder',
    },
  ]

  return (
    <div className="page">
      <header className="topbar">
        <div className="brand">FOODRUSH</div>
        <nav className="menu">
          <a href="#menu">Menu</a>
          <a href="#how">How it works</a>
          <a href="#review">Review</a>
        </nav>
        <button className="btn btn-outline">Sign In</button>
      </header>

      <section className="hero-section">
        <div className="hero-copy reveal">
          <p className="kicker">Fast delivery in your area</p>
          <h1>
            Delicious Food,
            <br />
            Delivered with Style.
          </h1>
          <p className="sub">
            Dat mon trong 30 giay, theo doi don hang real-time, va nhan uu dai moi ngay tu hon 500+
            nha hang doi tac.
          </p>
          <div className="hero-actions">
            <button className="btn btn-primary">Order Now</button>
            <button className="btn btn-ghost">Watch Demo</button>
          </div>
          <div className="stats">
            <div>
              <strong>4.9/5</strong>
              <span>Average rating</span>
            </div>
            <div>
              <strong>15k+</strong>
              <span>Daily orders</span>
            </div>
            <div>
              <strong>20 min</strong>
              <span>Average delivery</span>
            </div>
          </div>
        </div>

        <div className="hero-visual reveal-delay">
          <div className="plate-card large">
            <p>Chef Recommendation</p>
            <h3>Salmon Bowl Signature</h3>
            <span>Fresh, balanced, and ready in 18 minutes</span>
          </div>
          <div className="plate-card small float-card">
            <p>Today Discount</p>
            <h3>-35%</h3>
            <span>for all healthy meals</span>
          </div>
          <div className="radial"></div>
        </div>
      </section>

      <section className="category" id="menu">
        <div className="section-heading">
          <h2>Popular Categories</h2>
          <p>Chon mon yeu thich tu nhung danh muc duoc dat nhieu nhat hom nay</p>
        </div>
        <div className="category-grid">
          {categories.length === 0 && <p className="api-note">Dang cap nhat danh muc...</p>}
          {categories.map((item) => (
            <article className="category-card" key={item.name}>
              <h3>{item.name}</h3>
              <p>Delivery {item.time}</p>
              <span>{item.rating} rating</span>
            </article>
          ))}
        </div>
      </section>

      <section className="dishes">
        <div className="section-heading">
          <h2>Best Dishes This Week</h2>
          <p>Mon an duoc lay truc tiep tu MariaDB thong qua API Spring Boot</p>
        </div>
        {loading && <p className="api-note">Dang tai du lieu mon an...</p>}
        {error && <p className="api-note api-error">{error}</p>}
        <div className="dish-grid">
          {dishes.map((dish) => (
            <article className="dish-card" key={dish.id}>
              <span className="tag">{dish.tag}</span>
              <h3>{dish.name}</h3>
              <p>${Number(dish.price).toFixed(2)}</p>
              <small>
                {dish.category} | {dish.estimatedTime} | {Number(dish.rating).toFixed(1)} rating
              </small>
              <button className="btn btn-inline">Add to Cart</button>
            </article>
          ))}
        </div>
      </section>

      <section className="how" id="how">
        <div className="section-heading">
          <h2>How It Works</h2>
          <p>3 buoc don gian de thuong thuc bua an tuyet voi tai nha</p>
        </div>
        <div className="steps">
          <article>
            <strong>01</strong>
            <h3>Choose Your Meal</h3>
            <p>Kham pha menu lon voi bo loc thong minh va goi y theo so thich.</p>
          </article>
          <article>
            <strong>02</strong>
            <h3>Track In Real-time</h3>
            <p>Ban do truc quan cho phep theo doi tai xe va thoi gian giao du kien.</p>
          </article>
          <article>
            <strong>03</strong>
            <h3>Enjoy Fast Delivery</h3>
            <p>Mon an duoc giao nong hoi, dung gio, dung chat luong cam ket.</p>
          </article>
        </div>
      </section>

      <section className="review" id="review">
        <div className="section-heading">
          <h2>What Customers Say</h2>
          <p>Phan hoi thuc te tu nguoi dung sau moi don hang</p>
        </div>
        <div className="review-grid">
          {testimonials.map((item) => (
            <article key={item.name}>
              <p className="quote">&quot;{item.quote}&quot;</p>
              <h3>{item.name}</h3>
              <span>{item.role}</span>
            </article>
          ))}
        </div>
      </section>

      <section className="cta">
        <h2>Ready to Order Your Favorite Meal?</h2>
        <p>Download app de nhan uu dai cho don dau tien va free ship trong 7 ngay.</p>
        <div className="hero-actions">
          <button className="btn btn-primary">Get The App</button>
          <button className="btn btn-ghost">See Restaurants</button>
        </div>
      </section>

      <footer className="footer">
        <p>FoodRush Inc. 2026</p>
        <p>support@foodrush.app</p>
      </footer>
    </div>
  )
}

export default App
