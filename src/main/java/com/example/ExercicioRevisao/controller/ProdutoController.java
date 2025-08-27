package com.example.ExercicioRevisao.controller;

import com.example.ExercicioRevisao.exception.ProdutoNotFoundException;
import com.example.ExercicioRevisao.model.Produto;
import com.example.ExercicioRevisao.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;



    @GetMapping
    public String listaTodosProdutos(Model model){
        model.addAttribute("produtos", produtoService.getAllProdutos());
        return "index";
    }


    @GetMapping("/novo")
    public String adicionarProduto(Model model){
        model.addAttribute("produto", new Produto());
        return "formulario-produto";
    }

    @PostMapping
    public String adicionarProduto(Produto produto){
        produtoService.adicionarProduto(produto);
        return "redirect:/produtos";
    }

    @GetMapping
    @RequestMapping("/buscarPorNome")
    public String buscarPorNome(@RequestParam String nome, Model model){
        List<Produto> produto = Collections.singletonList(produtoService.findAllByNome(nome));
        model.addAttribute("produtos", List.of(produto));
        return "index";
    }



    @GetMapping("/editar/{id}")
    public String carregarFormularioEdicao(@PathVariable Long id, Model model) {
        Optional<Produto> produto = produtoService.findById(id);
        model.addAttribute("patio", produto);
        return "formulario-atualizar";
    }

    @PutMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Produto produto) throws ProdutoNotFoundException {
        produtoService.atualizarProduto(id, produto);
        return "redirect:/produtos";
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Produto> deletarPorId(@PathVariable Long id){
//        produtoService.deletarProduto(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<EntityModel<Produto>> atualizarParcialmente(
//            @PathVariable Long id,
//            @RequestBody Map<String, Object> updates) {
//        try {
//            Produto produtoAtualizado = produtoService.atualizarParcialmente(id, updates);
//            return ResponseEntity.ok(produtoAssembler.toModel(produtoAtualizado));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        } catch (ProdutoNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
