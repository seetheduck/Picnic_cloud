package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportEntity is a Querydsl query type for ReportEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportEntity extends EntityPathBase<ReportEntity> {

    private static final long serialVersionUID = 1564381997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportEntity reportEntity = new QReportEntity("reportEntity");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final QFleamarketEntity fleamarketEntity;

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> reviewNo = createNumber("reviewNo", Integer.class);

    public final QUserEntity userEntity;

    public QReportEntity(String variable) {
        this(ReportEntity.class, forVariable(variable), INITS);
    }

    public QReportEntity(Path<? extends ReportEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportEntity(PathMetadata metadata, PathInits inits) {
        this(ReportEntity.class, metadata, inits);
    }

    public QReportEntity(Class<? extends ReportEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fleamarketEntity = inits.isInitialized("fleamarketEntity") ? new QFleamarketEntity(forProperty("fleamarketEntity"), inits.get("fleamarketEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

