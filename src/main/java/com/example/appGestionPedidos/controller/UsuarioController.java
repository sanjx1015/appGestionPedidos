package com.example.appGestionPedidos.controller;

import com.example.appGestionPedidos.data.User;
import com.example.appGestionPedidos.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class UsuarioController {

    private final UserService userService;

    public UsuarioController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/usuarios/nuevo")
    public String formulario(HttpSession session){

        String rebote = verificarAdmin(session);
        if (rebote != null) return rebote;

        return "nuevoUsuario";
    }


    @PostMapping("/usuarios/nuevo")
    public String crear(@RequestParam("username") String username,
                        @RequestParam("name") String name,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model){

        String rebote = verificarAdmin(session);
        if (rebote != null) return rebote;

        Optional<User> creado = userService.registrar(username, name, password);

        if (creado.isEmpty()){
            model.addAttribute("error", "Este usuario ya existe");
            return "nuevoUsuario";
        }
        return "redirect:/adminhome";
    }

    private String verificarAdmin(HttpSession session){
        Object enSesion = session.getAttribute("user");
        if (enSesion == null) return "redirect:/login";
        User actual = (User) enSesion;

        if (actual.getRol() != User.Rol.ADMIN) return "redirect:/regularhome";
        return null;

    }
}
