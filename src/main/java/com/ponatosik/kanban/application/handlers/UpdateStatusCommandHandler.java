package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.UpdateStatusCommand;
import com.ponatosik.kanban.core.entities.Status;

@Handler(request = UpdateStatusCommand.class)
public class UpdateStatusCommandHandler implements RequestHandler<UpdateStatusCommand, Status> {
    private final StatusRepository StatusRepository;

    public UpdateStatusCommandHandler(StatusRepository StatusRepository) {
        this.StatusRepository = StatusRepository;
    }

    @Override
    public Status handle(UpdateStatusCommand command) {
        Status status = StatusRepository.findById(command.statusId()).orElseThrow();
        if(command.title() != null) {
            status.setCaption(command.title());
        }

        StatusRepository.save(status);
        return status;
    }
}

