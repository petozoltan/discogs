package pet.discogs.data.recording;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import pet.discogs.data.values.RecordingType;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Recording {

    /**
     * <p>        The default strategy is AUTO, which is most likely SEQUENCE.
     * </p><p>    SEQUENCE
     * <ul><li>   DOES: Uses a sequence in the database, and allocates Ids in chunks.
     * </li><li>  PROS: Works well with batch inserts, and does not require a round trip to the database for each Id generation.
     * </li><li>  CONS: Requires a sequence in the database, and may not work well with some databases that do not support sequences.
     * </li></ul>
     * </p><p>    IDENTITY
     * <ul><li>   DOES: Uses the database's identity column, and allocates Ids one at a time.
     * </li><li>  PROS: Works with all databases, and does not require a sequence.
     * </li><li>  CONS: May not work well with batch inserts, and requires a round trip to the database for each Id generation.
     * </li></ul>
     * </p>
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
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
