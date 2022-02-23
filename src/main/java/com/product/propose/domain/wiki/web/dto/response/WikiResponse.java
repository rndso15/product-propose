package com.product.propose.domain.wiki.web.dto.response;

import com.product.propose.domain.wiki.entity.PriceRecord;
import com.product.propose.domain.wiki.entity.reference.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WikiResponse {

    // Wiki
    private String title;

    // PriceRecord
    private List<PriceRecord> priceRecordGroup;

    // Wiki Tag
    private List<Tag> tagGroup;
}
