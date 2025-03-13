package com.example.demo.repository;

import com.example.demo.model.CommonSpace;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonSpaceRepository {
    private final List<CommonSpace> commonSpaces = new ArrayList<>();

    public CommonSpace save(CommonSpace commonSpace) {
        commonSpaces.add(commonSpace);
        return commonSpace;
    }

    public List<CommonSpace> findAll() {
        return commonSpaces;
    }

    public CommonSpace findById(Long id) {
        return commonSpaces.stream()
                .filter(cs -> cs.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public CommonSpace updateCommonSpace(CommonSpace commonSpace) {
        CommonSpace oldCommonSpace = findById(commonSpace.getId());

        if (oldCommonSpace != null) {
            oldCommonSpace.setSurface(commonSpace.getSurface());
            return oldCommonSpace;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return commonSpaces.removeIf(cs -> cs.getId().equals(id));
    }
}
