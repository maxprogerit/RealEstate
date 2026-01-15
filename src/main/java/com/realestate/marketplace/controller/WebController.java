package com.realestate.marketplace.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Value("${app.google.maps.api.key}")
    private String googleMapsApiKey;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "index";
    }

    @GetMapping("/properties")
    public String properties(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "properties";
    }

    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "map";
    }

    @GetMapping("/add-property")
    public String addProperty(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "add-property";
    }

    @GetMapping("/saved")
    public String saved(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "saved";
    }

    @GetMapping("/alerts")
    public String alerts(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "alerts";
    }
}
