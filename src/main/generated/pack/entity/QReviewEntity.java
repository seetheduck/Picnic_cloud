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

    public final BooleanPath rBlocked = createBoolean("rBlocked");

    public final NumberPath<Integer> rBlockedCnt = createNumber("rBlockedCnt", Integer.class);

    public final StringPath rCont = createString("rCont");

    public final DateTimePath<java.time.LocalDateTime> rCreateDate = createDateTime("rCreateDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> rDelDate = createDateTime("rDelDate", java.time.LocalDateTime.class);

    public final BooleanPath rDelIs = createBoolean("rDelIs");

    public final StringPath rId = createString("rId");

    public final StringPath rIp = createString("rIp");

    public final BooleanPath rLike = createBoolean("rLike");

    public final NumberPath<Integer> rLikeCnt = createNumber("rLikeCnt", Integer.class);

    public final NumberPath<Integer> rNo = createNumber("rNo", Integer.class);

    public final NumberPath<Integer> rPno = createNumber("rPno", Integer.class);

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

