package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFleamarketEntity is a Querydsl query type for FleamarketEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFleamarketEntity extends EntityPathBase<FleamarketEntity> {

    private static final long serialVersionUID = -1435288041L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFleamarketEntity fleamarketEntity = new QFleamarketEntity("fleamarketEntity");

    public final BooleanPath blocked = createBoolean("blocked");

    public final NumberPath<Integer> blockedCnt = createNumber("blockedCnt", Integer.class);

    public final QCategoryEntity categoryEntity;

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> createdate = createDateTime("createdate", java.time.LocalDateTime.class);

    public final BooleanPath favorite = createBoolean("favorite");

    public final NumberPath<Integer> favoriteCnt = createNumber("favoriteCnt", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedate = createDateTime("updatedate", java.time.LocalDateTime.class);

    public final QUserEntity userEntity;

    public QFleamarketEntity(String variable) {
        this(FleamarketEntity.class, forVariable(variable), INITS);
    }

    public QFleamarketEntity(Path<? extends FleamarketEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFleamarketEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFleamarketEntity(PathMetadata metadata, PathInits inits) {
        this(FleamarketEntity.class, metadata, inits);
    }

    public QFleamarketEntity(Class<? extends FleamarketEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryEntity = inits.isInitialized("categoryEntity") ? new QCategoryEntity(forProperty("categoryEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

