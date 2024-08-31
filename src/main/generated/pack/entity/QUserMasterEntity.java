package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserMasterEntity is a Querydsl query type for UserMasterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserMasterEntity extends EntityPathBase<UserMasterEntity> {

    private static final long serialVersionUID = -127743418L;

    public static final QUserMasterEntity userMasterEntity = new QUserMasterEntity("userMasterEntity");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath pw = createString("pw");

    public final BooleanPath signoutIs = createBoolean("signoutIs");

    public final DateTimePath<java.time.LocalDateTime> signupDate = createDateTime("signupDate", java.time.LocalDateTime.class);

    public QUserMasterEntity(String variable) {
        super(UserMasterEntity.class, forVariable(variable));
    }

    public QUserMasterEntity(Path<? extends UserMasterEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserMasterEntity(PathMetadata metadata) {
        super(UserMasterEntity.class, metadata);
    }

}

