package com.nvminh162.cms.content;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public List<Article> findAll() {
		return articleRepository.findAll();
	}

	public Article findById(Long id) {
		return articleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Article not found: " + id));
	}

	public Article create(Article article) {
		Instant now = Instant.now();
		article.setId(null);
		article.setCreatedAt(now);
		article.setUpdatedAt(now);
		if (article.getStatus() == null) {
			article.setStatus("DRAFT");
		}
		return articleRepository.save(article);
	}

	public Article update(Long id, Article updated) {
		Article existing = findById(id);
		existing.setTitle(updated.getTitle());
		existing.setBody(updated.getBody());
		existing.setStatus(updated.getStatus());
		existing.setUpdatedAt(Instant.now());
		return articleRepository.save(existing);
	}

	public void delete(Long id) {
		articleRepository.deleteById(id);
	}
}

