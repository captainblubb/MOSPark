export default interface NotificationJson {
    id: number;
    fromUserId: number;
    toUserId: number;
    notification: string;
    date: Date
}