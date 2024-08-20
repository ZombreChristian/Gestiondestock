package com.zombre.gestiondestock.service.impl;

import com.zombre.gestiondestock.dto.ArticleDto;
import com.zombre.gestiondestock.exception.EntityNotFoundException;
import com.zombre.gestiondestock.exception.ErrorCodes;
import com.zombre.gestiondestock.exception.InvalidEntityException;
import com.zombre.gestiondestock.repository.ArticleRepository;
import com.zombre.gestiondestock.service.ArticleService;
import com.zombre.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleServiceImp implements ArticleService {

    private ArticleRepository articleRepository;


    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (errors.isEmpty()) {
            log.error("Article n'est pas valide {}",dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
    }


    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID is vide");
            return null;
        }

        return articleRepository.findById(id).map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return null;
    }

    @Override
    public List<ArticleDto> findAll() {
        return List.of();
    }

    @Override
    public void delete(Integer id) {

    }
}
