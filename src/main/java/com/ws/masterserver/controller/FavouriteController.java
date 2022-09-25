package com.ws.masterserver.controller;

import com.ws.masterserver.dto.customer.favourite.request.FavouriteRequest;
import com.ws.masterserver.utils.base.WsController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favourite")
@RequiredArgsConstructor
@Slf4j
public class FavouriteController  extends WsController {

    @GetMapping("/listFavourite")
    public ResponseEntity<?> getListFavourite(){
        log.info("START API /api/v1/favourite/listFavourite");
        return ResponseEntity.ok(service.favouriteService.getListFavourite(getCurrentUser()));
    }

    @PostMapping
    public ResponseEntity<?> createFavourite(@RequestBody FavouriteRequest req){
        log.info("START API /api/v1/favourite");
        return ResponseEntity.ok(service.favouriteService.createFavourite(getCurrentUser(),req));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "API delete address")
    public ResponseEntity<?> deleteFavourite(@PathVariable("id") String id){
        log.info("------ start api delete favourite -----");
        service.favouriteService.deleteFavourite(getCurrentUser(),id);
        return ResponseEntity.ok("Delete favourite successfully !!");
    }
}
