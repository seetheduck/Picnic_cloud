package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -864132604L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final BooleanPath accountDeleteIs = createBoolean("accountDeleteIs");

    public final ListPath<FleamarketEntity, QFleamarketEntity> fleaMarkets = this.<FleamarketEntity, QFleamarketEntity>createList("fleaMarkets", FleamarketEntity.class, QFleamarketEntity.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath pw = createString("pw");

    public final DateTimePath<java.time.LocalDateTime> signupDate = createDateTime("signupDate", java.time.LocalDateTime.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

