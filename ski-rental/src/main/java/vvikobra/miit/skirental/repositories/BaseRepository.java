package vvikobra.miit.skirental.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public abstract class BaseRepository<E, ID> {

    private final Class<E> entityClass;

    protected BaseRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(E entity) {
        entityManager.persist(entity);
    }

    public Optional<E> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public List<E> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public Optional<E> findByName(String name) {
        List<E> result = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.name = :name", entityClass)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    @Transactional
    public void update(E entity) {
        entityManager.merge(entity);
    }
}
