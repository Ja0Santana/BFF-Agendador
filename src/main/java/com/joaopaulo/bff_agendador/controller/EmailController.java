package com.joaopaulo.bff_agendador.controller;


import com.joaopaulo.bff_agendador.business.EmailService;
import com.joaopaulo.bff_agendador.business.dto.out.TarefaDTOresponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefaDTOresponse tarefaDTOresponse,
                                            @RequestHeader(name = "Authorization", required = false) String token) {
        emailService.enviarEmail(tarefaDTOresponse, token);
        return ResponseEntity.ok().build();
    }
}
