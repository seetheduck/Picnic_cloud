package pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pack.repository.PlaceRepository;

@Service
public class AsyncPlaceService {
    @Autowired
    private LikesService likesService;

    @Autowired
    private PlaceRepository placeRepository;

    @Async
    public void updatePlaceLikeCount(int placeNo) {
        int likeCount = likesService.getPlaceLikesCount(placeNo);

        placeRepository.findById(placeNo).ifPresent(place -> {
            place.setLikeCnt(likeCount);
            placeRepository.save(place);
        });
    }
}
