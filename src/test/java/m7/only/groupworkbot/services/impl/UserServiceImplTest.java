package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.user.User;
import m7.only.groupworkbot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    public static final Long CORRECT_CHAT_ID = 156446416464L;
    public static final User CORRECT_USER = new User(CORRECT_CHAT_ID);

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl out;

    @Test
    void shouldSaveAndReturnCorrectUser() {
        out.add(CORRECT_USER);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertEquals(CORRECT_CHAT_ID, capturedUser.getChatId());
    }

    @Test
    void shouldReturnCorrectUser() {
        when(userRepositoryMock.findByChatId(CORRECT_CHAT_ID)).thenReturn(Optional.of(CORRECT_USER));
        assertEquals(CORRECT_USER, out.findUserByChatIdOrCreateNew(CORRECT_CHAT_ID));
        verify(userRepositoryMock, times(1)).findByChatId(any());
        verify(userRepositoryMock, times(0)).save(any());
    }

    @Test
    void shouldReturnNewUser() {
        when(userRepositoryMock.findByChatId(CORRECT_CHAT_ID)).thenReturn(Optional.empty());
        when(userRepositoryMock.save(any())).thenReturn(CORRECT_USER);
        User returnedUser = out.findUserByChatIdOrCreateNew(CORRECT_CHAT_ID);
        assertEquals(CORRECT_CHAT_ID, returnedUser.getChatId());
        assertFalse(returnedUser.getStepParent());
        assertFalse(returnedUser.getTrialSuccess());
        assertFalse(returnedUser.getTrialSuccessInformed());
        assertFalse(returnedUser.getTrialExtended());
        assertFalse(returnedUser.getTrialExtendedInformed());
        assertFalse(returnedUser.getTrialFailure());
        assertFalse(returnedUser.getTrialFailureInformed());
        verify(userRepositoryMock, times(1)).findByChatId(any());
        verify(userRepositoryMock, times(1)).save(any());
    }
}