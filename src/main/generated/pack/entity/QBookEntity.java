package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookEntity is a Querydsl query type for BookEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookEntity extends EntityPathBase<BookEntity> {

    private static final long serialVersionUID = -159817054L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookEntity bookEntity = new QBookEntity("bookEntity");

    public final StringPath author = createString("author");

    public final StringPath bookName = createString("bookName");

    public final QCategoryEntity category;

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath publicationYear = createString("publicationYear");

    public final StringPath publisher = createString("publisher");

    public final ArrayPath<byte[], Byte> thumbnail = createArray("thumbnail", byte[].class);

    public QBookEntity(String variable) {
        this(BookEntity.class, forVariable(variable), INITS);
    }

    public QBookEntity(Path<? extends BookEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookEntity(PathMetadata metadata, PathInits inits) {
        this(BookEntity.class, metadata, inits);
    }

    public QBookEntity(Class<? extends BookEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategoryEntity(forProperty("category")) : null;
    }

}

