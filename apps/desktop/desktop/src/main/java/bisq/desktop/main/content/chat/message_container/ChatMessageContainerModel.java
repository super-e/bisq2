package bisq.desktop.main.content.chat.message_container;

import bisq.chat.ChatChannel;
import bisq.chat.ChatChannelDomain;
import bisq.chat.ChatMessage;
import bisq.chat.ChatService;
import bisq.user.profile.UserProfile;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class ChatMessageContainerModel implements bisq.desktop.common.view.Model {
    private final BooleanProperty chatDialogEnabled = new SimpleBooleanProperty(true);

    private final ObjectProperty<ChatChannel<? extends ChatMessage>> selectedChannel = new SimpleObjectProperty<>();
    private final StringProperty textInput = new SimpleStringProperty("");
    private final BooleanProperty userProfileSelectionVisible = new SimpleBooleanProperty();
    private final ObjectProperty<Boolean> focusInputTextField = new SimpleObjectProperty<>();
    private final ObservableList<UserProfile> mentionableUsers = FXCollections.observableArrayList();
    // TODO mentionableChatChannels not filled with data
    private final ObservableList<ChatChannel<?>> mentionableChatChannels = FXCollections.observableArrayList();
    private final ChatChannelDomain chatChannelDomain;
    private final ChatService chatService;
    @Nullable
    private ChatMessage selectedChatMessage;

    public ChatMessageContainerModel(ChatChannelDomain chatChannelDomain, ChatService chatService) {
        this.chatChannelDomain = chatChannelDomain;
        this.chatService = chatService;
    }

    String getChannelTitle(ChatChannel<?> chatChannel) {
        return chatService.findChatChannelService(chatChannel)
                .map(service -> service.getChannelTitle(chatChannel))
                .orElse("");
    }

    public void setSelectedChatMessage(@Nullable ChatMessage selectedChatMessage) {
        this.selectedChatMessage = selectedChatMessage;
    }
}