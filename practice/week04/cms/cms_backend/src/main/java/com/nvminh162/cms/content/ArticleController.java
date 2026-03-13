package com.nvminh162.cms.content;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:5173")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping
	public List<Article> list() {
		return articleService.findAll();
	}

	@GetMapping("/{id}")
	public Article get(@PathVariable Long id) {
		return articleService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Article> create(@RequestBody Article article) {
		Article created = articleService.create(article);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	public Article update(@PathVariable Long id, @RequestBody Article article) {
		return articleService.update(id, article);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		articleService.delete(id);
		return ResponseEntity.noContent().build();
	}
}

