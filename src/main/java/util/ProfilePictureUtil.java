package util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class ProfilePictureUtil {
    public static final String[] PROFILE_PICTURES = {
            "alpaca_icon.png", "elephant_icon.png", "flamingo_icon.png", "giraffe_icon.png",
            "parrot_icon.png", "rhino_icon.png", "tiger_icon.png"
    };

    private static Image getProfilePicture(int participantId) {
        int i = Math.abs(participantId % PROFILE_PICTURES.length);
        String imagePath = "/images/" + PROFILE_PICTURES[i];
        Image image = new Image(Objects.requireNonNull(ProfilePictureUtil.class.getResourceAsStream(imagePath)));
        if (image.isError()) {
            System.err.println("ERROR: Error loading image: " + imagePath + ". Using default image.");
            image = new Image(Objects.requireNonNull(ProfilePictureUtil.class.getResourceAsStream("/images/" + PROFILE_PICTURES[0])));
        }
        return image;
    }

    public static ImageView getProfilePictureView(int participantId, int width, int height) {
        ImageView imageView = new ImageView(getProfilePicture(participantId));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private ProfilePictureUtil() {
    }
}
