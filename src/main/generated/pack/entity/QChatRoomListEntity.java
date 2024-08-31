package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomListEntity is a Querydsl query type for ChatRoomListEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomListEntity extends EntityPathBase<ChatRoomListEntity> {

    private static final long serialVersionUID = 411833162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomListEntity chatRoomListEntity = new QChatRoomListEntity("chatRoomListEntity");

    public final QChatRoomEntity chatRoomEntity;

    public final NumberPath<Integer> chatRoomId = createNumber("chatRoomId", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QChatRoomListEntity(String variable) {
        this(ChatRoomListEntity.class, forVariable(variable), INITS);
    }

    public QChatRoomListEntity(Path<? extends ChatRoomListEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomListEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomListEntity(PathMetadata metadata, PathInits inits) {
        this(ChatRoomListEntity.class, metadata, inits);
    }

    public QChatRoomListEntity(Class<? extends ChatRoomListEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoomEntity = inits.isInitialized("chatRoomEntity") ? new QChatRoomEntity(forProperty("chatRoomEntity"), inits.get("chatRoomEntity")) : null;
    }

}

