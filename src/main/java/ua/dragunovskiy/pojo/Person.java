package ua.dragunovskiy.pojo;


import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


@Getter
@Setter
public class Person {

//    private ObjectId id;

//    @BsonProperty(value = "person_id")
//    private int personId;
    private String name;

//    public Person(ObjectId id, int personId, String name) {
//        this.id = id;
//        this.personId = personId;
//        this.name = name;
//    }

    public Person() {}
}
