export default function Alert(
    severity,
    text
) {
    return (
        <Alert severity={severity}>
            {text}
        </Alert>
    );
}