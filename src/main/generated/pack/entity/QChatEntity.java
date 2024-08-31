package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatEntity is a Querydsl query type for ChatEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatEntity extends EntityPathBase<ChatEntity> {

    private static final long serialVersionUID = 179006417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatEntity chatEntity = new QChatEntity("chatEntity");

    public final StringPath cBuyerId = createString("cBuyerId");

    public final DateTimePath<java.time.LocalDateTime> cCreateDate = createDateTime("cCreateDate", java.time.LocalDateTime.class);

    public final StringPath cMessageCont = createString("cMessageCont");

    public final NumberPath<Integer> cMessageNo = createNumber("cMessageNo", Integer.class);

    public final EnumPath<ChatEntity.MessageType> cMessageType = createEnum("cMessageType", ChatEntity.MessageType.class);

    public final NumberPath<Integer> cNo = createNumber("cNo", Integer.class);

    public final StringPath cSellerId = createString("cSellerId");

    public final QFleamarketEntity fleamarketEntity;

    public QChatEntity(String variable) {
        this(ChatEntity.class, forVariable(variable), INITS);
    }

    public QChatEntity(Path<? extends ChatEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatEntity(PathMetadata metadata, PathInits inits) {
        this(ChatEntity.class, metadata, inits);
    }

    public QChatEntity(Class<? extends ChatEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fleamarketEntity = inits.isInitialized("fleamarketEntity") ? new QFleamarketEntity(forProperty("fleamarketEntity"), inits.get("fleamarketEntity")) : null;
    }

}

