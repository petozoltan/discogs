package pet.discogs.data.recording;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import pet.discogs.data.values.RecordingType;

import java.util.Objects;

@Entity
public class Recording {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    // TODO Create valid column name with JPA
    private Integer yearr;

    private RecordingType type;

    public Recording() {
    }

    public Recording(String title, Integer yearr, RecordingType type) {
        this.title = title;
        this.yearr = yearr;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Integer getYearr() {
        return yearr;
    }

    public void setYearr(final Integer yearr) {
        this.yearr = yearr;
    }

    public RecordingType getType() {
        return type;
    }

    public void setType(final RecordingType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, yearr, type);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recording recording)) {
            return false;
        }
        return Objects.equals(id, recording.id)
                && Objects.equals(title, recording.title)
                && Objects.equals(yearr, recording.yearr)
                && type == recording.type
                ;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", yearr=" + yearr +
                ", type=" + type +
                '}';
    }
}
