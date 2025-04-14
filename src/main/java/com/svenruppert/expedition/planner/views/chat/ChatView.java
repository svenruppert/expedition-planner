package com.svenruppert.expedition.planner.views.chat;


import com.svenruppert.expedition.planner.components.AbstractView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Route("chat")
public class ChatView extends AbstractView<VerticalLayout> {

    public static final String MENU_ITEM_CHAT = "mainlayout.menuitem.chat";
    public static final String TITLE = "chat.title";

    private final MessageList list;
    private final List<MessageListItem> messageItems = new ArrayList<>();

//    private final ChatStreamingService chatStreamingService;

    private String chatId ;

    public ChatView() {
        super(TITLE);

        list = new MessageList();

        list.setSizeFull();

        MessageInput input = new MessageInput();
        input.addSubmitListener(this::onSubmit);
        input.setWidthFull();

        getContent().add(list, input);
        getContent().setSizeFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        chatId = UUID.randomUUID().toString();
    }

    private void onSubmit(MessageInput.SubmitEvent submitEvent) {
        addMessage(submitEvent.getValue(), "User");

        var messageListItem = new MessageListItem("", Instant.now(), "Greekseek R1");
        messageItems.add(messageListItem);
        list.setItems(messageItems);

        //ToDo: add ai response to message list
//        chatStreamingService.answerStream(chatId, submitEvent.getValue())
//                .subscribe(answer -> {
//                    getUI().ifPresent(ui -> ui.access(() -> {
//                        var text = messageListItem.getText();
//                        messageListItem.setText(text + answer);
//                    }));
//                });
    }

    private void addMessage(String message, String userName) {
        var messageListItem = new MessageListItem(message, Instant.now(), userName);
        messageItems.add(messageListItem);
        list.setItems(messageItems);
    }
}
