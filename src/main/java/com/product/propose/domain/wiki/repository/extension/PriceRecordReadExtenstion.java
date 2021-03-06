package com.product.propose.domain.wiki.repository.extension;

import com.product.propose.global.data.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface PriceRecordReadExtenstion {

    PageResponse readPageByWikiId(Long wikiId, Pageable pageable);
}
