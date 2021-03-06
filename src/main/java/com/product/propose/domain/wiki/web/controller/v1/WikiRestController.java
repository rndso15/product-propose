package com.product.propose.domain.wiki.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.propose.domain.account.entity.aggregate.Account;
import com.product.propose.domain.wiki.entity.aggregate.Wiki;
import com.product.propose.domain.wiki.repository.PriceRecordRepository;
import com.product.propose.domain.wiki.repository.WikiRepository;
import com.product.propose.domain.wiki.service.WikiService;
import com.product.propose.domain.wiki.web.dto.request.PriceRegisterRequest;
import com.product.propose.domain.wiki.web.dto.request.PriceUpdateRequest;
import com.product.propose.domain.wiki.web.dto.request.WikiRegisterRequest;
import com.product.propose.domain.wiki.web.dto.request.WikiUpdateRequest;
import com.product.propose.domain.wiki.web.dto.response.WikiSummaryResponse;
import com.product.propose.global.annotation.CurrentAccount;
import com.product.propose.global.api.RestApiController;
import com.product.propose.global.data.assertion.CommonAssert;
import com.product.propose.global.data.dto.PageResponse;
import com.product.propose.global.data.dto.PaginationRequest;
import com.product.propose.global.exception.dto.enums.ErrorCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;


@RestController(value = "wikiRestController")
@RequestMapping(value = "/api/v1/wiki", produces = MediaType.APPLICATION_JSON_VALUE)
public class WikiRestController extends RestApiController {

    // Service
    private final WikiService wikiService;

    // Repository
    private final WikiRepository wikiRepository;
    private final PriceRecordRepository priceRecordRepository;

    public WikiRestController(ObjectMapper objectMapper, WikiService wikiService, WikiRepository wikiRepository, PriceRecordRepository priceRecordRepository) {
        super(objectMapper);
        this.wikiService = wikiService;
        this.wikiRepository = wikiRepository;
        this.priceRecordRepository = priceRecordRepository;
    }

    // ============================================  Create - Post  ===================================================

    @PostMapping("/register")
    private ResponseEntity<String> registerWiki(@CurrentAccount Account account,
                                                @RequestBody @Valid WikiRegisterRequest registerRequest) {
        CommonAssert.exists(account, ErrorCode.ACCOUNT_NOT_FOUND);

        // Register Logic
        Wiki result = wikiService.registerWiki(account.getId(), registerRequest.getWikiCreateData());
        return createRestResponse(new HashMap<>() {{
            put("result", result.getId());
        }});
    }

    @PostMapping("/{wikiId}/price/register")
    private ResponseEntity<String> registerPriceRecord(@PathVariable Long wikiId,
                                                       @CurrentAccount Account account,
                                                       @RequestBody @Valid PriceRegisterRequest request) {
        CommonAssert.exists(account, ErrorCode.ACCOUNT_NOT_FOUND);

        // Register PriceRecord
        Wiki result = wikiService.addPriceRecord(wikiId, account.getId(), request.getPriceRecordCreateForm());

        return createRestResponse(new HashMap<>() {{
            put("result", result.getId());
        }});
    }

    @PostMapping(value = "/{wikiId}/update/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> registerWikiImage(@PathVariable Long wikiId,
                                                @RequestPart MultipartFile[] images) {
        // Wiki
        Wiki result = wikiService.updateWikiImage(wikiId, images);
        return createRestResponse(new HashMap<>() {{
            put("result", result.getId());
        }});
    }

    // ============================================  Read - Get  ======================================================

    @GetMapping("/{wikiId}/read")
    private ResponseEntity<String> readWikiInfo(@PathVariable Long wikiId) {
        // get WikiResponse
        WikiSummaryResponse wikiSummary = wikiService.readWiki(wikiId);

        // get Page PriceRecord
        PageResponse priceRecords = priceRecordRepository.readPageByWikiId(wikiId, PageRequest.of(0, DEFAULT_PAGE_SIZE));

        return createRestResponse(new HashMap<>() {{
            put("summary", wikiSummary);
            put("priceRecords", priceRecords);
        }});
    }

    @GetMapping("/read")
    private ResponseEntity<String> readPage(@ModelAttribute @Valid PaginationRequest request) {
        PageRequest page = PageRequest.of(request.getPage(), request.getSize());
        PageResponse wikiPageResponse = wikiRepository.readWikiPageResponse(page);

        return createRestResponse(wikiPageResponse.getContent());
    }

    // ============================================  Update - Put  ====================================================

    @PutMapping("/{wikiId}/update")
    private ResponseEntity<String> putWiki(@PathVariable Long wikiId,
                                           @RequestBody @Valid WikiUpdateRequest request) {
        // get WikiResponse
        Wiki result = wikiService.updateWiki(wikiId, request.getWikiUpdateData());

        return createRestResponse(new HashMap<>() {{
            put("result", result.getId());
        }});
    }

    @PutMapping("/{wikiId}/price/{recordId}/update")
    private ResponseEntity<String> putPriceRecord(@PathVariable Long wikiId,
                                                  @PathVariable Long recordId,
                                                  @CurrentAccount Account account,
                                                  @RequestBody @Valid PriceUpdateRequest request) {
        CommonAssert.exists(account, ErrorCode.ACCOUNT_NOT_FOUND);

        // Update PriceRecord
        Wiki result = wikiService.updatePriceRecord(wikiId, recordId, account.getId(), request.getPriceUpdateData());

        return createRestResponse(new HashMap<>() {{
            put("result", result.getId());
        }});
    }

    // ============================================  Delete - delete  =================================================
}
