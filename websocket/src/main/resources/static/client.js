// <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
// <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
//
// <script>
//     const socket = new SockJS("/ws");
//     const stompClient = Stomp.over(socket);
//
//     stompClient.connect( {}, function () {
//     const orderId = 123; // 예시 주문 ID
//     stompClient.subscribe(`/topic/delivery-status/${orderId}`, function (message) {
//     const statusMessage = JSON.parse(message.body);
//     console.log("배송 상태 업데이트:", statusMessage.status);
//         });
//     });
// </script>