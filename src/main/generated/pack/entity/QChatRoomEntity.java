package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEntity is a Querydsl query type for ChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEntity extends EntityPathBase<ChatRoomEntity> {

    private static final long serialVersionUID = 605610380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomEntity chatRoomEntity = new QChatRoomEntity("chatRoomEntity");

    public final StringPath buyerId = createString("buyerId");

    public final NumberPath<Integer> fileNo = createNumber("fileNo", Integer.class);

    public final NumberPath<Integer> fleaMarketNo = createNumber("fleaMarketNo", Integer.class);

    public final QMessageEntity messageEntity;

    public final NumberPath<Integer> messageNo = createNumber("messageNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath sellerId = createString("sellerId");

    public QChatRoomEntity(String variable) {
        this(ChatRoomEntity.class, forVariable(variable), INITS);
    }

    public QChatRoomEntity(Path<? extends ChatRoomEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomEntity(PathMetadata metadata, PathInits inits) {
        this(ChatRoomEntity.class, metadata, inits);
    }

    public QChatRoomEntity(Class<? extends ChatRoomEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.messageEntity = inits.isInitialized("messageEntity") ? new QMessageEntity(forProperty("messageEntity"), inits.get("messageEntity")) : null;
    }

}

