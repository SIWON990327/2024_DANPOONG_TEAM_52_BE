package com.groom.orbit.ai.dao;

import java.util.List;
import java.util.Optional;

import com.google.protobuf.Struct;
import com.groom.orbit.ai.dao.vector.Vector;

public interface VectorStore {

  void save(Long key, List<Float> vectors, Struct metadata);

  Optional<Vector> findById(Long id);

  List<Vector> findSimilar(List<Float> vector);
}
