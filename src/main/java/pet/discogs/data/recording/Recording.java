package pet.discogs.data.recording;

import jakarta.persistence.*;
import pet.discogs.data.common.Printable;
import pet.discogs.data.group.Group;
import pet.discogs.data.values.RecordingType;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Recording extends Printable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @Column(name = "released")
    private Integer year;

    @Enumerated(EnumType.STRING)
    private RecordingType type;

    @ManyToOne(optional = false)
    private Group group;

    public Recording() {
    }

    public Recording(String title, Integer year, RecordingType type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(final Group group) {
        this.group = group;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public RecordingType getType() {
        return type;
    }

    public void setType(final RecordingType type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, title, year, type);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recording recording)) {
            return false;
        }
        return Objects.equals(id, recording.id)
                && Objects.equals(group, recording.group)
                && Objects.equals(title, recording.title)
                && Objects.equals(year, recording.year)
                && type == recording.type
                ;
    }

    @Override
    public String toString() {
        return "Recording" +
                "{ id=" + id +
                ", group='" + group.getName() + "'" +
                ", title='" + title + "'" +
                ", year=" + year +
                ", type=" + type +
                " }";
    }

    @Override
    public String getShortName() {
        return year + " " + title;
    }
}
