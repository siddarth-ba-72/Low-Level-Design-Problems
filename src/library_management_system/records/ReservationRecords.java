package library_management_system.records;

import library_management_system.models.BookReservation;
import library_management_system.models.BookReservationStatus;

import java.util.ArrayList;
import java.util.List;

import static java.time.Instant.now;

public class ReservationRecords {

    private final List<BookReservation> bookReservationRecords;

    private ReservationRecords() {
        this.bookReservationRecords = new ArrayList<>();
    }

    public static ReservationRecords getInstance() {
        return ReservationRecordHolder.INSTANCE;
    }

    public List<BookReservation> getBookReservationRecords() {
        return new ArrayList<>(bookReservationRecords);
    }

    public void addReservationRecord(BookReservation reservationRecord) {
        bookReservationRecords.add(reservationRecord);
    }

    public List<BookReservation> getOverdueReservations() {
        return bookReservationRecords.stream()
                .filter(res ->
                        (res.getBookReservationStatus() == BookReservationStatus.OVERDUE) ||
                        (res.getBookReservationStatus() == BookReservationStatus.BORROWED && res.getReturnDate().toInstant().isBefore(now()))
                )
                .toList();
    }

    public List<BookReservation> getQueuedReservations() {
        return bookReservationRecords.stream()
                .filter(res -> res.getBookReservationStatus() == BookReservationStatus.QUEUED)
                .toList();
    }

    public void removeReservationRecord(BookReservation reservationRecord) {
        bookReservationRecords.remove(reservationRecord);
    }

    private static class ReservationRecordHolder {
        private static final ReservationRecords INSTANCE = new ReservationRecords();
    }

}
