package movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class FilmsController {

    @Autowired
    private FilmsRepositoty filmsRepository;

    @GetMapping("/films")
    public String filmsMain(Model model) {
        Iterable<Films> films = filmsRepository.findAll();
        model.addAttribute("users", films);
        return "list-of-films";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("users", "Main page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("users", "About");
        return "about";
    }

    @GetMapping("/add")
    public String filmsAdd(Model model) {
        return"films-add";
    }

    @PostMapping("/add")
    public String filmPostadd(@RequestParam String name, @RequestParam String genre, @RequestParam Integer releaseDate, @RequestParam String url, @RequestParam String description, Model model) {
        Films films = new Films(name, genre, releaseDate, url, description);
        filmsRepository.save(films);
        return"redirect:/films";
    }

    @GetMapping("/films/{id}")
    public String filmInfo(@PathVariable(value = "id") Integer id, Model model) {
        if(!filmsRepository.existsById(id)) {
            return"redirect:/films";
        }
        Optional<Films> film = filmsRepository.findById(id);
        ArrayList<Films> res = new ArrayList<>();
        film.ifPresent(res::add);
        model.addAttribute("user", res);
        return"films-description";
    }

    @PostMapping("/films/{id}/delete")
    public String filmDelete(@PathVariable(value = "id") Integer id, Model model) {
        Films film = filmsRepository.findById(id).orElseThrow(() -> new FilmNotExistException(id));
        filmsRepository.delete(film);
        return"redirect:/films";
    }

    @GetMapping("/films/{id}/edit")
    public String filmEdit(@PathVariable(value = "id") Integer id, Model model) {
        if(!filmsRepository.existsById(id)) {
            return"redirect:/films";
        }

        Optional<Films> film = filmsRepository.findById(id);
        ArrayList<Films> res = new ArrayList<>();
        film.ifPresent(res::add);
        model.addAttribute("user", res);
        return"films-edit";
    }

    @PostMapping("/films/{id}/edit")
    public String filmPostUpdate(@PathVariable(value = "id") Integer id, @RequestParam String name, @RequestParam String genre, @RequestParam Integer releaseDate, @RequestParam String url, @RequestParam String description, Model model) {
        Films films = filmsRepository.findById(id).orElseThrow(() -> new FilmNotExistException(id));
        films.setName(name);
        films.setGenre(genre);
        films.setReleaseDate(releaseDate);
        films.setUrl(url);
        films.setDescription(description);
        filmsRepository.save(films);
        return"redirect:/films";
    }
}