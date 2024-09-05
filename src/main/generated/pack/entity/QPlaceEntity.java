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

    public final StringPath address = createString("address");

    public final StringPath description = createString("description");

    public final StringPath entranceFee = createString("entranceFee");

    public final StringPath image = createString("image");

    public final NumberPath<Integer> likeCnt = createNumber("likeCnt", Integer.class);

    public final BooleanPath likeIs = createBoolean("likeIs");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath operationTime = createString("operationTime");

    public final StringPath placeType = createString("placeType");

    public final NumberPath<Float> point = createNumber("point", Float.class);

    public final NumberPath<Integer> reviewCount = createNumber("reviewCount", Integer.class);

    public final StringPath tel = createString("tel");

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

