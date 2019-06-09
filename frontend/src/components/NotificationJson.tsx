export default interface NotificationJson {
    id: number;
    senderId: number;
    recipientId: number;
    content: string;
    date: string;
    dismissed: boolean;
    dismissedDate: string;
}
