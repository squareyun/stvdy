package com.ssafy.ssap.repository;
import com.ssafy.ssap.domain.studyroom.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecifications {

    public static Specification<Room> hasTitle(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }
    public static Specification<Room> isValid(boolean isValid) {
        return (root, query, builder) -> builder.equal(root.get("isValid"), isValid);
    }
}