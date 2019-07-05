export default interface NotificationJson {
    id: number;
    senderId: number;
    recipientId: number;
    content: string;
    dismissed: boolean;
    dismissedDate: string;
}
