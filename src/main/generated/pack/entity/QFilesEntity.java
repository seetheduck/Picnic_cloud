package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFilesEntity is a Querydsl query type for FilesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFilesEntity extends EntityPathBase<FilesEntity> {

    private static final long serialVersionUID = -2121205372L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFilesEntity filesEntity = new QFilesEntity("filesEntity");

    public final QFleamarketEntity fleamarketEntity;

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath path = createString("path");

    public final DateTimePath<java.time.LocalDateTime> uploadDate = createDateTime("uploadDate", java.time.LocalDateTime.class);

    public final StringPath userId = createString("userId");

    public QFilesEntity(String variable) {
        this(FilesEntity.class, forVariable(variable), INITS);
    }

    public QFilesEntity(Path<? extends FilesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFilesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFilesEntity(PathMetadata metadata, PathInits inits) {
        this(FilesEntity.class, metadata, inits);
    }

    public QFilesEntity(Class<? extends FilesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fleamarketEntity = inits.isInitialized("fleamarketEntity") ? new QFleamarketEntity(forProperty("fleamarketEntity"), inits.get("fleamarketEntity")) : null;
    }

}

