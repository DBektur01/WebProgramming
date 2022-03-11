package movie;

import org.springframework.data.repository.CrudRepository;

public interface FilmsRepositoty extends CrudRepository<Films, Integer> {
}
