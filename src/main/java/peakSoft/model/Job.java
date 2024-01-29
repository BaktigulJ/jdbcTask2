package peakSoft.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Job {
    private Long id;
    private String position;      //("Mentor","Management","Instructor")
    private String profession;    //("Java","JavaScript")
    private String  description; //Backend developer,Fronted developer;
    private int experience;

    public Job(String position, String profession, String description, int experience) {
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }
}
