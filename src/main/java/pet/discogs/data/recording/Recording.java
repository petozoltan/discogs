package pet.discogs.data.recording;

import pet.discogs.data.group.Group;
import pet.discogs.data.values.RecordingType;

import java.util.Objects;

//@Entity
public class Recording {

    //    @Id
//    @GeneratedValue
    private Long id;

    private String title;

    //    @ManyToOne
//    @JoinColumn(name = "group_id")
    private Group group;

    private Integer year;

    private RecordingType type;

    public Recording() {
    }

    public Recording(String title, Group group, Integer year, RecordingType type) {
        this.title = title;
        this.group = group;
        this.year = year;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Group getGroup() {
        return group;
    }

    public Integer getYear() {
        return year;
    }

    public RecordingType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, group, year, type);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Recording recording)) {
            return false;
        }
        return Objects.equals(id, recording.id) && Objects.equals(title, recording.title) && Objects.equals(group, recording.group) && Objects.equals(year, recording.year) && type == recording.type;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", group=" + group.getName() +
                ", year=" + year +
                ", type=" + type +
                '}';
    }
}
