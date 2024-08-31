package pack.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlaceEntity is a Querydsl query type for PlaceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceEntity extends EntityPathBase<PlaceEntity> {

    private static final long serialVersionUID = 470936596L;

    public static final QPlaceEntity placeEntity = new QPlaceEntity("placeEntity");

    public final StringPath pAddress = createString("pAddress");

    public final StringPath pCategory = createString("pCategory");

    public final StringPath pDay = createString("pDay");

    public final StringPath pExplain = createString("pExplain");

    public final StringPath pImage = createString("pImage");

    public final BooleanPath pLike = createBoolean("pLike");

    public final NumberPath<Integer> pLikeCnt = createNumber("pLikeCnt", Integer.class);

    public final StringPath pName = createString("pName");

    public final NumberPath<Integer> pNo = createNumber("pNo", Integer.class);

    public final StringPath pPay = createString("pPay");

    public final NumberPath<Float> pPoint = createNumber("pPoint", Float.class);

    public final StringPath pTel = createString("pTel");

    public QPlaceEntity(String variable) {
        super(PlaceEntity.class, forVariable(variable));
    }

    public QPlaceEntity(Path<? extends PlaceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlaceEntity(PathMetadata metadata) {
        super(PlaceEntity.class, metadata);
    }

}

