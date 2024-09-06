package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoomListEntity is a Querydsl query type for ChatRoomListEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomListEntity extends EntityPathBase<ChatRoomListEntity> {

    private static final long serialVersionUID = 411833162L;

    public static final QChatRoomListEntity chatRoomListEntity = new QChatRoomListEntity("chatRoomListEntity");

    public final NumberPath<Integer> chatRoomNo = createNumber("chatRoomNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath userId = createString("userId");

    public QChatRoomListEntity(String variable) {
        super(ChatRoomListEntity.class, forVariable(variable));
    }

    public QChatRoomListEntity(Path<? extends ChatRoomListEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomListEntity(PathMetadata metadata) {
        super(ChatRoomListEntity.class, metadata);
    }

}

