package cs.hacettepe.bbm102;

public class Score {
    private int userId;
    private int filmId;
    private int point;

    public Score(int userId, int filmId, int point) {
        this.userId = userId;
        this.filmId = filmId;
        this.point = point;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
