const API_URL = '/api/restaurants';

// --- 뷰 관리 함수: DOMContentLoaded 이후에만 실행되도록 보장 ---
function showView(viewId) {
    document.querySelectorAll('.view').forEach(view => {
        view.classList.remove('active');
    });
    document.getElementById(viewId).classList.add('active');
}

// --- API 통신 및 데이터 처리 ---

// 1. 맛집 목록을 불러와서 화면에 표시
async function fetchRestaurants() {
    const listElement = document.getElementById('restaurant-list');
    listElement.innerHTML = '<li class="empty-message">데이터를 불러오는 중입니다...</li>';

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const restaurants = await response.json();
        listElement.innerHTML = '';

        if (restaurants.length === 0) {
            listElement.innerHTML = '<li class="empty-message">등록된 맛집이 없습니다.</li>';
            return;
        }

        restaurants.forEach(restaurant => {
            const listItem = document.createElement('li');
            listItem.classList.add('restaurant-item');
            listItem.setAttribute('data-id', restaurant.id);
            listItem.innerHTML = `
                <div class="item-info">
                    <strong>${restaurant.name}</strong> 
                    <span>(${restaurant.area})</span>
                </div>
                `;
            listElement.appendChild(listItem);
        });

    } catch (error) {
        console.error('맛집 목록 로드 실패:', error);
        listElement.innerHTML = `<li class="empty-message" style="color: red;">데이터 로드 실패. 서버 상태를 확인하세요.</li>`;
    }
}

// 2. 새로운 맛집을 추가
async function addRestaurant(event) {
    event.preventDefault(); // ✅ 버튼 작동 문제 해결 핵심: 폼 제출 시 새로고침 방지

    const form = document.getElementById('restaurant-form');
    // Number 타입 변환 시 오류 방지를 위해 try-catch 내부에서 변환
    const name = form.name.value;
    const area = form.area.value;
    const memo = form.memo.value;

    try {
        const newRestaurant = {
            name,
            area,
            memo,
            // parseFloat 대신 Number() 사용 및 필수 입력 확인
            lat: Number(form.lat.value),
            lng: Number(form.lng.value)
        };

        // 필수 값 검사 (HTML required와 별개로 한번 더 검사)
        if (!name || !area || isNaN(newRestaurant.lat) || isNaN(newRestaurant.lng)) {
            alert('이름, 지역, 위도, 경도는 필수 입력 항목이며, 위도/경도는 숫자여야 합니다.');
            return;
        }

        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newRestaurant)
        });

        if (response.status === 201) {
            alert('맛집이 성공적으로 등록되었습니다!');
            form.reset();
            await fetchRestaurants();
            showView('main-view'); // 메인 화면으로 이동
        } else {
            // 서버 유효성 검사 실패 (400 Bad Request) 처리 포함
            const errorText = await response.text();
            alert(`등록 실패: ${errorText || '서버 내부 오류 (500 에러)'}`);
        }

    } catch (error) {
        console.error('맛집 등록 중 오류 발생:', error);
        alert('맛집 등록 중 네트워크 오류가 발생했습니다.');
    }
}

// 3. 특정 맛집 상세 정보를 불러와 표시
async function showDetail(id) {
    const detailContent = document.getElementById('detail-content');
    const deleteDetailBtn = document.getElementById('delete-detail-btn');
    detailContent.innerHTML = '<p style="text-align: center;">상세 정보 로딩 중...</p>';
    deleteDetailBtn.setAttribute('data-id', id); // 삭제 버튼에 ID 미리 저장

    try {
        const response = await fetch(`${API_URL}/${id}`);

        if (response.status === 404) {
            alert('찾으시는 맛집 정보가 없습니다.');
            await fetchRestaurants(); // 목록 갱신
            showView('main-view');
            return;
        }
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const restaurant = await response.json();

        detailContent.innerHTML = `
            <p><strong>이름:</strong> ${restaurant.name}</p>
            <p><strong>지역:</strong> ${restaurant.area}</p>
            <p><strong>위도:</strong> ${restaurant.lat}</p>
            <p><strong>경도:</strong> ${restaurant.lng}</p>
            <div class="memo-area">
                <p><strong>메모:</strong></p>
                <p>${restaurant.memo || '메모 없음'}</p>
            </div>
        `;
        showView('detail-view'); // 상세 화면 표시

    } catch (error) {
        console.error('상세 정보 로드 실패:', error);
        alert('상세 정보를 불러오는 데 실패했습니다.');
        showView('main-view');
    }
}


// 4. 맛집 삭제
async function deleteRestaurant(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (response.status === 204) {
            alert('맛집이 성공적으로 삭제되었습니다.');
            await fetchRestaurants();
            showView('main-view');
        } else {
            // 404 Not Found 등 다른 오류 처리
            alert('맛집 삭제에 실패했거나, 이미 존재하지 않는 맛집입니다.');
            await fetchRestaurants();
            showView('main-view');
        }
    } catch (error) {
        console.error('맛집 삭제 중 오류 발생:', error);
        alert('맛집 삭제 중 네트워크 오류가 발생했습니다.');
    }
}


// --- 이벤트 리스너 등록 ---
document.addEventListener('DOMContentLoaded', () => {
    // 폼 제출 이벤트 (맛집 추가)
    document.getElementById('restaurant-form').addEventListener('submit', addRestaurant);

    // '맛집 추가하기' 버튼 클릭 (뷰 전환)
    document.getElementById('go-to-add-btn').addEventListener('click', () => {
        document.getElementById('restaurant-form').reset();
        showView('add-view');
    });

    // '목록으로 돌아가기' 버튼 및 '취소' 버튼 클릭 (뷰 전환)
    document.querySelectorAll('.back-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            showView('main-view');
        });
    });

    // 목록 클릭 이벤트 (상세 조회)
    document.getElementById('restaurant-list').addEventListener('click', (e) => {
        // 클릭된 요소 또는 가장 가까운 li 요소를 찾음
        const listItem = e.target.closest('.restaurant-item');
        if (listItem) {
            const id = listItem.getAttribute('data-id');
            showDetail(id);
        }
    });

    // 상세 화면의 '삭제' 버튼 클릭
    document.getElementById('delete-detail-btn').addEventListener('click', (e) => {
        const id = e.target.getAttribute('data-id');
        if (confirm('정말로 이 맛집을 삭제하시겠습니까?')) {
            deleteRestaurant(id);
        }
    });

    // 초기 로드
    fetchRestaurants();
    showView('main-view');
});