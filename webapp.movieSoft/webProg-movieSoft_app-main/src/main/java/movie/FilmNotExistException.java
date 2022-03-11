package movie;

public class FilmNotExistException extends RuntimeException {
    public FilmNotExistException(Integer id) {
        super("No such id exists " + id);
    }
}
