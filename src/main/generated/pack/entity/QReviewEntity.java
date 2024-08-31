package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReviewEntity is a Querydsl query type for ReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewEntity extends EntityPathBase<ReviewEntity> {

    private static final long serialVersionUID = -174060975L;

    public static final QReviewEntity reviewEntity = new QReviewEntity("reviewEntity");

    public final BooleanPath blocked = createBoolean("blocked");

    public final NumberPath<Integer> blockedCnt = createNumber("blockedCnt", Integer.class);

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> delDate = createDateTime("delDate", java.time.LocalDateTime.class);

    public final BooleanPath delIs = createBoolean("delIs");

    public final StringPath id = createString("id");

    public final StringPath ip = createString("ip");

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final BooleanPath likeIs = createBoolean("likeIs");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> placeNo = createNumber("placeNo", Integer.class);

    public final NumberPath<Float> point = createNumber("point", Float.class);

    public QReviewEntity(String variable) {
        super(ReviewEntity.class, forVariable(variable));
    }

    public QReviewEntity(Path<? extends ReviewEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReviewEntity(PathMetadata metadata) {
        super(ReviewEntity.class, metadata);
    }

}

