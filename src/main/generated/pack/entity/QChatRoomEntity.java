package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoomEntity is a Querydsl query type for ChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEntity extends EntityPathBase<ChatRoomEntity> {

    private static final long serialVersionUID = 605610380L;

    public static final QChatRoomEntity chatRoomEntity = new QChatRoomEntity("chatRoomEntity");

    public final StringPath buyerId = createString("buyerId");

    public final NumberPath<Integer> fleaMarketNo = createNumber("fleaMarketNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath sellerId = createString("sellerId");

    public QChatRoomEntity(String variable) {
        super(ChatRoomEntity.class, forVariable(variable));
    }

    public QChatRoomEntity(Path<? extends ChatRoomEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomEntity(PathMetadata metadata) {
        super(ChatRoomEntity.class, metadata);
    }

}

