package com.example.appGestionPedidos.controller;

import com.example.appGestionPedidos.data.Producto;
import com.example.appGestionPedidos.data.User;
import com.example.appGestionPedidos.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
         this.productoService = productoService;
    }


    @GetMapping("/productos")
    public String productos(HttpSession session, Model model){

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        List<Producto> lista = productoService.listar();
        model.addAttribute("productos", lista);
        return "productos";
    }


    @GetMapping("/productos/nuevo")
    public String formulario(HttpSession session){
        String rebote = verificarAdmin(session);
        if (rebote != null)return rebote;
        return "nuevoProducto";
    }

    @PostMapping("/productos/nuevo")
    public String crear(@RequestParam("nombre")String nombre,
                        @RequestParam("descripcion")String descripcion,
                        @RequestParam("precio")BigDecimal precio,
                        HttpSession session,
                        Model model){
        String rebote = verificarAdmin(session);
        if(rebote != null) return rebote;

        Optional<Producto> creado = productoService.crear(nombre,descripcion,precio);

        if(creado.isEmpty()){
            model.addAttribute("error", "Revisar nombre y/o precio");
            return "nuevoProducto";

        }
        return "redirect:/productos";
    }

    private String verificarAdmin(HttpSession session){
        Object enSesion = session.getAttribute("user");
        if(enSesion == null) return "redirect:/login";
        User actual = (User) enSesion;
        if(actual.getRol() != User.Rol.ADMIN) return "redirect:/adminhome";
        return null;
    }
}
