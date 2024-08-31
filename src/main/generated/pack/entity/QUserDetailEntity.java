package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserDetailEntity is a Querydsl query type for UserDetailEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserDetailEntity extends EntityPathBase<UserDetailEntity> {

    private static final long serialVersionUID = 1544243445L;

    public static final QUserDetailEntity userDetailEntity = new QUserDetailEntity("userDetailEntity");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> childAge = createNumber("childAge", Integer.class);

    public final StringPath email = createString("email");

    public final BooleanPath gender = createBoolean("gender");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> signoutDate = createDateTime("signoutDate", java.time.LocalDateTime.class);

    public final BooleanPath userstat = createBoolean("userstat");

    public QUserDetailEntity(String variable) {
        super(UserDetailEntity.class, forVariable(variable));
    }

    public QUserDetailEntity(Path<? extends UserDetailEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserDetailEntity(PathMetadata metadata) {
        super(UserDetailEntity.class, metadata);
    }

}

